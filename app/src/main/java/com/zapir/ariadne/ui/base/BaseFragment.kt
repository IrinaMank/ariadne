package com.zapir.ariadne.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import org.slf4j.LoggerFactory

abstract class BaseFragment : MvpAppCompatFragment() {
    companion object {
        private val PROGRESS_TAG = "bf_progress"
    }

    abstract val layoutRes: Int
    val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    override fun onAttach(context: Context?) {
        logger.info("Lifecycle ${javaClass.simpleName} onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.info("Lifecycle ${javaClass.simpleName} onCreate")
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { restoreState(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.info("Lifecycle ${javaClass.simpleName} onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    protected open fun restoreState(state: Bundle) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger.info("Lifecycle ${javaClass.simpleName} onCreateView")
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        logger.info("Lifecycle ${javaClass.simpleName} onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        logger.info("Lifecycle ${javaClass.simpleName} onCreate")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        logger.info("Lifecycle ${javaClass.simpleName} onStart")
        super.onStart()
    }

    override fun onResume() {
        logger.info("Lifecycle ${javaClass.simpleName} onResume")
        super.onResume()
    }

    override fun onPause() {
        logger.info("Lifecycle ${javaClass.simpleName} onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        logger.info("Lifecycle ${javaClass.simpleName} onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        logger.info("Lifecycle ${javaClass.simpleName} onStop")
        super.onStop()
    }

    override fun onDestroy() {
        logger.info("Lifecycle ${javaClass.simpleName} onDestroy")
        super.onDestroy()
    }

    override fun onDestroyView() {
        logger.info("Lifecycle ${javaClass.simpleName} onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        logger.info("Lifecycle ${javaClass.simpleName} onDetach")
        super.onDetach()
    }

//    protected fun showSnackMessage(message: String) {
//        view?.let {
//            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
//            val messageTextView = snackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
//            messageTextView.setTextColor(Color.WHITE)
//            snackbar.show()
//        }
//    }
}