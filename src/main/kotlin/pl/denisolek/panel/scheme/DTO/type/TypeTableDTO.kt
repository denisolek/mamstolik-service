package pl.denisolek.panel.scheme.DTO.type

import pl.denisolek.core.scheme.SchemeItem
import pl.denisolek.core.scheme.SchemeItem.TableType

data class TypeTableDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var floorName: String,
        var subType: TableType,
        var position: SchemePositionDTO,
        var details: SchemeDetailsDTO,
        var spotInfo: SchemeSpotInfoDTO
) {
    constructor(item: SchemeItem) : this(
            id = item.id,
            floorId = item.floor.id,
            floorName = item.floor.name,
            subType = item.tableType!!,
            details = SchemeDetailsDTO(
                    width = item.width,
                    heigth = item.height,
                    rotation = item.rotation
            ),
            position = SchemePositionDTO(
                    x = item.x,
                    y = item.y
            ),
            spotInfo = SchemeSpotInfoDTO(
                    id = item.spot?.id,
                    number = item.spot!!.number,
                    capacity = item.spot!!.capacity,
                    minPeopleNumber = item.spot!!.minPeopleNumber
            )
    )
}