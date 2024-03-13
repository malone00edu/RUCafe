package com.example.rucafe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.rucafe.model.Donut
import com.example.rucafe.model.DonutType

/**
 * This ViewModel is responsible for holding a list of donut objects to be observed
 * and changed with respect to quantity.
 * @author Taze Balbosa
 */
class DonutViewModel : ViewModel() {
    private val donuts: MutableLiveData<List<Donut>> = MutableLiveData()

    init {
        // Initialize your donut list here
        val donutList: ArrayList<Donut> = ArrayList()
        // Add donut objects to the list
        donutList.add(Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.flavor_1))
        donutList.add(Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.flavor_2))
        donutList.add(Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.flavor_3))
        donutList.add(Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.flavor_4))
        donutList.add(Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.flavor_1))
        donutList.add(Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.flavor_2))
        donutList.add(Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.flavor_3))
        donutList.add(Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.flavor_4))
        donutList.add(Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.flavor_1))
        donutList.add(Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.flavor_2))
        donutList.add(Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.flavor_3))
        donutList.add(Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.flavor_4))
        donuts.value = donutList
    }

    /**
     * Provides a way to access the donuts data from outside the ViewModel
     */
    fun getDonuts(): LiveData<List<Donut>> {
        return donuts
    }

    // Contains constants to be accessed in a static way
    // ZERO is used to define the initial quantity of donuts
    companion object {
        private const val ZERO = 0
    }
}
