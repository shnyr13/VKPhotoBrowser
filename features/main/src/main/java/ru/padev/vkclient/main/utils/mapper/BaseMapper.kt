package ru.padev.vkclient.main.utils.mapper

import java.util.*

abstract class BaseMapper<VO, ITEM> {
    abstract fun transform(item: ITEM): VO
    fun transform(items: List<ITEM>): MutableList<VO> {
        val objects: MutableList<VO> = ArrayList(items.size)
        for (item in items) {
            objects.add(transform(item))
        }
        return objects
    }
}