package com.eddymy1304.sacalacuenta.data.models

import com.eddymy1304.sacalacuenta.data.db.CuentaWithDetalleCuenta

data class CuentaWithDetalleView(
    val cuenta: CuentaView = CuentaView(),
    val listDetCuenta: List<DetalleCuentaView> = listOf()
) {
    constructor(response : CuentaWithDetalleCuenta) : this(
        cuenta = CuentaView(response.cuenta),
        listDetCuenta = response.listDetalle.map { DetalleCuentaView(it) }
    )
}
