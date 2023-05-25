package furhatos.app.furgui

import furhatos.app.furgui.BuyFruit
import furhatos.app.furgui.Fruit
import furhatos.app.furgui.FruitList
import furhatos.app.furgui.RequestOptions
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

var selectedPosition = " "

val ChoosingGrid = state() {

    onEntry {
        random(
            { furhat.ask("What grid would you like to choose?") }
        )
    }

    // Users clicked any of our buttons
    onEvent(CLICK_GRID) {
        val gridPos = it.get("data") as String
        if (gridPos != "") {
            furhat.say("Great!, you have chosen $gridPos")
            selectedPosition =  gridPos
            furhat.ask("What would you like to place in $gridPos?")
        }
        // Directly respond with the value we get from the event, with a fallback
        furhat.say("You pressed ${it.get("data") ?: "something I'm not aware of" }")
    }

    onResponse<RequestOptions> {
        furhat.say("We have ${GridPosition().optionsToText()}")
        furhat.ask("What would you like to select?")
    }

    onResponse<ChooseGrid> {
        val gridPos = it.intent.gridPos
        if (gridPos != null) {
           send(SelectGrid(gridPos))
            furhat.say("Great!, you have chosen $gridPos")
            selectedPosition = gridPos.optionsToText()
            furhat.ask("What would you like to place in $gridPos?")
        }
        else {
            propagate()
        }
    }

    onResponse<ChooseGardenObject> {
        val gardenObj = it.intent.gardenObj
        if (gardenObj != null && selectedPosition != "") {
            send(AddGardenState(gridPosition = selectedPosition, gardenObject = gardenObj))
            furhat.say("Great!, you have chosen $gardenObj to be placed in $selectedPosition")
        }
        else {
            propagate()
        }
    }
}

