package space.rungeun.android_escpos_thermalprinter_photobox.controller


import space.rungeun.android_escpos_thermalprinter_photobox.model.Photo

object PhotoManager {
    private val photos = mutableListOf<Photo>()

    fun addPhoto(photo: Photo) {
        photos.add(photo)
    }

    fun getAllPhotos(): List<Photo> = photos

    fun clear() {
        photos.clear()
    }
}