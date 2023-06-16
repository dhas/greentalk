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

val StartingQuestions = state() {

    onEntry {
        furhat.say("There are a few questions I'd like to ask you before we start.")
        furhat.ask( "First of all, would you like a more traditional garden, or something more modern?")
    }

    onResponse<GardenTypeStatement> {
        val gardenType = it.intent.gardenType
        furhat.say("OK, we'll go with a $gardenType style!")
        goto(ChoosingGrid)
    }

}

//val ChoosingGardenItems = state() {
//
//    onEntry {
//        furhat.ask("What would you like in your garden?")
//    }
//
//    onResponse<FillGridPosition> {
//        furhat.say("Okay, you want ${it.intent}")
////        users.current.order.adjoin(it.intent)
////        goto(CheckOrder)
//    }
//
//}

val DoYouWant: State = state() {

    onEntry{
        furhat.ask("Do you want to make any changes or add anything?")
    }

    onResponse<No> {
        furhat.say("OK, then it seems we are done with the design.")
        goto(AskRender)
    }

    onResponse<Yes> {
        goto(ChoosingGrid)
    }
}

val AskRender: State = state() {

    onEntry {
        furhat.ask("Would you like to render the garden based on our design?")
    }

    onResponse<No> {
        furhat.say("OK, goodbye.")
    }

    onResponse<Yes> {
        furhat.say("Sorry, I actually can't do that bit just yet.")
    }

}

val ChoosingGrid: State = state() {

    onEntry {
        random(
            { furhat.ask {
                random {
                    + "Which part of your garden would you like to choose?"
                    + "Which area should we take a look at?"
                }
            }}
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
            selectedPosition = gridPos.toText() //.optionsToText()
            furhat.ask("What would you like to place in $gridPos?")
        }
        else {
            propagate()
        }
    }

    onResponse<ChooseGardenObject> {
        val gardenObj = it.intent.gardenObj
        if (gardenObj != null && selectedPosition != "") {
            // Add object to position in array
                // not done yet
            // Send to GUI
            send(AddGardenState(gridPosition = selectedPosition, gardenObject = gardenObj))
            furhat.say("Great!, you have chosen $gardenObj to be placed in $selectedPosition")
            goto(DoYouWant)
        }
        else {
            propagate()
        }
    }
}

