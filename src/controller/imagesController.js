const { format} = require('../config/database');
const processFile = require('../middleware/uploadImages');
const imageModel = require('../models/images');
const { Storage } = require('@google-cloud/storage');
const storage = new Storage();
const bucket = storage.bucket('images_user_request');

const uploadImage = async (req, res) => {
  try {
    await processFile(req);
    const originalname = req.file.originalname;

    if (!req.file) {
      return res.status(400).send({
          message: 'Please upload a file'
      });
  }

    const blob = bucket.file(originalname);
    const blobStream = blob.createWriteStream({
      resumable: false,
    });

    blobStream.on("error", (err) => {
      res.status(500).send({
        message: err.message
      });
    });

    blobStream.on("finish", async (data) => {
        const publicUrl = format(
          `https://storage.googleapis.com/${bucket.name}/${blob.name}`
        );

        // create url image into database
        await imageModel.uploadImage(publicUrl);
        
        try {
          // make the file to public
          await bucket.file(originalname).makePublic();
        } catch {
          return res.status(500).send({
            message: `Uploaded the file successfully: ${originalname}, but public access is denied!`,
            url_images: publicUrl,
          });
        }

        res.status(200).send({
          message: "Uploaded the file successfully: " + originalname,
          url_images: publicUrl,
        });
      });

    blobStream.end(req.file.buffer);
  } catch (err) {
    res.status(500).send({
      message: `Could not upload the file: ${originalname}. ${err}`,
    });
  }
};


const getAllImages = async (req, res) => {
  try {
    res.status(202).json({
      message: "berhasil"
    })
  } catch (error) {
    res.status(404).json({
      message: "tidak ada"
    })
  }
}
// blobStream.end(req.file.buffer);
// // add images to database
// const id_food = req.body;
// const images_url = `https://storage.googleapis.com/${bucket.name}/${blob.name}`;
// const result = await imageModel.uploadImage(id_food, images_url);

// res.json({
//     message: 'data berhasil diubah',
//     data: result
// })


module.exports = {
  // addImages,
  uploadImage,
  getAllImages
};
// const uploadImage = async (req, res) => {
//     try {
//         await processFile(req. res);

//         if (!req.file) {
//             return res.status(400).send({message: 'Please upload a file'});
//         }

//         blobStream.on('err', (err) => {
//             res.status(500).send({message: err.message});
//         });
//         blobStream.on('finish', async (data) => {
//             //create URL file access via HTTP
//             const publicUrl = format(
//                 `https://storage.googleapis.com/${bucket.name}/${blob.name}`
//             );

//             try {
//                 // make the file to public
//                 await bucket.file(req.file.originalname).makePublic();
//             } catch{
//                 return res.status(500).send({
//                     message: `Uploaded the file successfully: ${req.file.originalname}, but public access is denied!`,
//                     url: publicUrl,
//                 });
//             }

//             res.status(200).send({
//                 message: "Uploaded the file successfully: " + req.file.originalname,
//                 url: publicUrl,
//             });
//         });

//         blobStream.end(req.file.buffer);
//     }catch (err) {
//         if (err.code == 'LIMIT_FILE_SIZE'){
//             res.status(500).send({
//                 message: "File size cannot be larger than 2MB!",
//             });
//         }

//         res.status(500).send({
//             message: `Could not upload the file: ${req.file.originalname}. ${err}`,
//         });
//     }
// };

// const addImages = async (req, res) => {
//     try {
//         // Ambil file dari request
//         const file = req.file;
//         if (!file) {
//             return res.status(400).send('No file uploaded.');
//         }

//         // Simpan file di Google Cloud Storage
//         const bucket = storage.bucket(bucketName);
//         const blob = bucket.file(file.originalname);
//         const blobStream = blob.createWriteStream();
//         blobStream.end(file.buffer);

//         res.status(200).json('File uploaded and database updated.');
//     } catch (error) {
//         console.error(error);
//         res.status(500).json('Internal Server Error');
//     }
// };