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
        val buttons : List<String>,
        val inputFields: List<String>
) : Event()

// Event used to pass data to GUI
class SelectGrid(
        val select : GridPosition
) : Event()

class AddGardenObject(
        val gridPosition :  GridPosition,
        val gardenObject : GardenObject
) : Event()



