package service

class Relay {
    fun nextImage(): Boolean{
        // tell runner program to start up the next image
        return false
    }

    fun previousImage(): Boolean{
        // tell runner program to start up previous image
        return false
    }

    fun killCommand(): Boolean{
        // tell runner program to end
        return false
    }
}