package com.zapir.ariadne.ui.findway

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.zapir.ariadne.R
import com.zapir.ariadne.presenter.search.SearchViewModel
import com.zapir.ariadne.ui.base.BaseFragment
import com.zapir.ariadne.ui.route.RouteFragment
import com.zapir.ariadne.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_findway.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FindWayFragment: BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_findway

    val pointsViewModel: SearchViewModel by sharedViewModel()//расшаренный вьюмодел позволяет
    // общаться двум фрагментам.  Здесь мы меняем from/to в SearchFragment и получаем изменения
    // в этом. Возможно стоит создать для этого отдельный вьюмодел, в котором буду только две
    // LiveData
    // сама констуркция by sharedViewModel() - функция Koin, которая сама подставит экземпляр
    // объекта в это место

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textview.setOnClickListener { onFromTextViewClicked() }
        textview2.setOnClickListener { onToTextViewClicked() }
        textview.text = "Default"
        pointsViewModel.from.observe(this, Observer {
            textview.text = it?.name
        })// создаем подписчика на изменения данных
        textview2.text = "Default"
        pointsViewModel.to.observe(this, Observer {
            textview2.text = it?.name
        })

        btn.setOnClickListener {
            if (!textview.text.isNullOrEmpty() && !textview2.text.isNullOrEmpty()) {
                val intent = RouteFragment.startIntent(pointsViewModel.from.value,
                        pointsViewModel.to.value)
                activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, intent)
                        ?.addToBackStack(null)
                        ?.commit()
            }

        }
    }

    private fun onFromTextViewClicked() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, SearchFragment.startIntent("from"))//фу фу хардкод.
                // Потом тут будут константы
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun onToTextViewClicked() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, SearchFragment.startIntent("to"))
                ?.addToBackStack(null)
                ?.commit()
    }
}