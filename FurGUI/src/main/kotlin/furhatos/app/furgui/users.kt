package furhatos.app.furgui

import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User

//class GardenData (
//    var gardenType : GardenType? = null //,
////    var gridData: GridData = GridData()
//)
//
//val User.garden : GardenData
//    get() = data.getOrPut(GardenData::class.qualifiedName, GardenData())

var User.gardenType by NullSafeUserDataDelegate { " " }

