package com.alksoft.controldeconsumoelectrico.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FacturaDiario {
    @Embedded
    public Invoice invoice;
    @Relation(
            parentColumn = "IdFactura",
            entityColumn = "IdFactura"
    )
    public Daily diarioKwh;
}
