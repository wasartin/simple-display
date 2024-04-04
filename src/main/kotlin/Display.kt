import service.ImageManipulation
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.File
import java.time.LocalTime
import kotlin.system.exitProcess

class Display(private val pic: BufferedImage) : Window(Frame()) {

    var displayCurrentPoster = true

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                when (e.button){
                    MouseEvent.BUTTON1 -> { // Left click
                        //Tell Runner Program this is done
                        exitProcess(0)
                    }
                    MouseEvent.BUTTON2 -> { // Middle Click
                        // tell program to go back one.
                        exitProcess(0)
                    }
                    MouseEvent.BUTTON3 -> { // Right Click
                        // tell program to skip forward
                        exitProcess(0)
                    }
                }
            }
        })
    }

    override fun paint(g: Graphics) {
        g.color = Color.BLACK
        g.drawRect(0, 0, width - 1, height - 1)
        g.drawImage(pic, 0, 0, width, height, this)
    }

    companion object{
        private var displayCurrentPoster = true

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            println("Startup")
            var imageToDisplay = "/Users/wsartin/dev/workshop/photo-db/posters/jpg/akira.jpeg"
            var intervalInMinutes = 15
            if (args.isNotEmpty()) {
                imageToDisplay = args[0]
                intervalInMinutes = args[1].toInt()
                println("New intervalInMinutes: $intervalInMinutes")
            }
            println("Image: $imageToDisplay")
            run(imageToDisplay, intervalInMinutes)
        }

        private fun run(image: String, intervalInMinutes: Int) {
        try {
                val startTime = LocalTime.now()
                val endTime = startTime.plusMinutes(intervalInMinutes.toLong())
                showPoster(File(image)) // add function to check if this can even run
                while (displayCurrentPoster) {
                    Thread.sleep(5)
                    if (LocalTime.now().isAfter(endTime)) {
                        displayCurrentPoster = false
                    }
                }
            } catch (e: Exception) {
                println("What the hell is going on here?")
                println(e)
            }
        }

        private fun setUp(): GraphicsDevice {
            val graphicEnv = GraphicsEnvironment.getLocalGraphicsEnvironment()
            val mainScreen = graphicEnv.defaultScreenDevice
            if (!mainScreen.isFullScreenSupported) {
                println("Full screen mode not supported")
                exitProcess(1)
            }
            return mainScreen
        }

        fun showPoster(image: File) {
            val screen = setUp()
            val rotatedImage = ImageManipulation().rotateImage(image)
            screen.fullScreenWindow = Display(rotatedImage)
        }
    }
}