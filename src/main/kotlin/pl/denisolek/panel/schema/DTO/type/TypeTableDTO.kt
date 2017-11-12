package pl.denisolek.panel.schema.DTO.type

import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.TableType

data class TypeTableDTO(
        var id: Int? = null,
        var floorId: Int? = null,
        var subType: TableType,
        var position: SchemaPositionDTO,
        var details: SchemaDetailsDTO,
        var spotInfo: SchemaSpotInfoDTO
) {
    constructor(item: SchemaItem) : this(
            id = item.id,
            floorId = item.floor.id,
            subType = item.tableType!!,
            details = SchemaDetailsDTO(
                    width = item.width,
                    heigth = item.height,
                    rotation = item.rotation
            ),
            position = SchemaPositionDTO(
                    x = item.x,
                    y = item.y
            ),
            spotInfo = SchemaSpotInfoDTO(
                    id = item.spot?.id,
                    number = item.spot!!.number,
                    capacity = item.spot!!.capacity,
                    minPeopleNumber = item.spot!!.minPeopleNumber
            )
    )
}