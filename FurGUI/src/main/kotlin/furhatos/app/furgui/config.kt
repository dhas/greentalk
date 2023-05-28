package furhatos.app.furgui

import furhatos.event.Event
import furhatos.skills.HostedGUI

/*
    Variables and events
 */
val PORT = 1234 // GUI Port
val SPEECH_DONE = "SpeechDone"

// Event used to pass data to GUI
class DataDelivery(
        val rows : List<String>,
        val cols: List<String>,
        val gardenObjects: List<String>
) : Event()

// Event used to pass data to GUI
class SendImage(
        val image: String
) : Event()


