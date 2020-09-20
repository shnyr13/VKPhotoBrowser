package ru.padev.vkclient.main.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LaunchAppResponse(
    @SerializedName("card_cost_refund") @Expose
    val cardCostRefund: Boolean = false,

    @SerializedName("status_order")
    @Expose
    val statusOrder: Boolean = true,

    @SerializedName("status_card_reg")
    @Expose
    val statusCardReg: Boolean = true,

    @SerializedName("status_card_virtual")
    @Expose
    val statusCardVirtual: Boolean = true,

    @SerializedName("status_card_cost_refund")
    @Expose
    val statusCardCostRefund: Boolean = true,

    @SerializedName("status_loyalty")
    @Expose
    val statusLoyalty: Boolean = true,

    @SerializedName("notification")
    @Expose
    val notification: LaunchAppResponseNotification? = null
)