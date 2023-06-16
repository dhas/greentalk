package furhatos.app.furgui

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.util.Language

class GridPosition : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        val gridPos = mutableListOf<String>()
        for (r in rows){for (c in cols) gridPos.add("$r$c")}
        return gridPos
    }

}

class ChooseGrid(var gridPos : GridPosition? = null) : Intent() {

    override fun getExamples(lang: Language): List<String> {
        return listOf("@gridPos", "I select @gridPos", "I choose @gridPos")
    }
}

class GardenObject : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return gardenObjects
    }
}

class ChooseGardenObject(var gardenObj : GardenObject? = null) : Intent() {

    override fun getExamples(lang: Language): List<String> {
        return listOf("@gardenObj", "I select @gardenObj", "I choose @gardenObj", "Maybe a @gardenObj?")
    }
}

class Fruit : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "orange", "apple", "cherimoya")
    }
}

class BuyFruit(var fruits : FruitList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@fruits", "I want @fruits", "I would like @fruits", "I want to buy @fruits")
    }
}

class RequestOptions: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("What options do you have?",
            "What fruits do you have?",
            "What are the alternatives?",
            "What do you have?")
    }
}

class GardenType : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return gardenTypes
    }
}

class GardenTypeStatement(var gardenType : GardenType? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I prefer @gardenType styles.",
            "@gardenType.",
            "A @gardenType garden please.",
            "Something more @gardenType")
    }
}

class FruitList : ListEntity<QuantifiedFruit>()

class QuantifiedFruit(
    var count : Number? = Number(1),
    var fruit : Fruit? = null) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@count @fruit", "@fruit")
    }

    override fun toText(): String {
        return generate("$count " + if (count?.value == 1) fruit?.value else "${fruit?.value}" + "s")
    }
}

class FillGridPosition : Intent(), TextGenerator {

    var gridPos : GridPosition? = null

    var gardenObj : GardenObject? = null

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I would like a bush in square B2",
            "Let's put a tree in position A3",
            "I want a pond in C1")
    }

    override fun toText(lang : Language) : String {
        return generate(lang, "${if (gardenObj != null) "[$gardenObj]" else "something"} ${if (gridPos != null) "[in $gridPos]" else "somewhere"}")
    }


    override fun toString(): String {
        return toText()
    }
}
