package furhatos.app.furgui

import furhatos.app.furgui.BuyFruit
import furhatos.app.furgui.Fruit
import furhatos.app.furgui.FruitList
import furhatos.app.furgui.RequestOptions
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

var selectedPosition : GridPosition? = null

val ChoosingGrid = state() {

    onEntry {
        random(
            { furhat.ask("What grid would you like to choose?") }
        )
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
            selectedPosition = gridPos
            furhat.ask("What would you like to place in $gridPos?")
        }
        else {
            propagate()
        }
    }

    onResponse<ChooseGardenObject> {
        val gardenObj = it.intent.gardenObj
        if (gardenObj != null) {
            send(AddGardenObject(gridPosition = selectedPosition!!, gardenObject = gardenObj))
            furhat.say("Great!, you have chosen $gardenObj to be placed in $selectedPosition")
        }
        else {
            propagate()
        }
    }
}

