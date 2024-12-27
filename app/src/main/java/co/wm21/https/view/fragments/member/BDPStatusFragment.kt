package co.wm21.https.view.fragments.member

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.networks.Models.application.BDPStatusData
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse
import co.wm21.https.databinding.FragmentBDPBinding
import co.wm21.https.utils.CheckInternetConnection
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.helpers.User
import co.wm21.https.presenter.application.BDPStatusPresenter
import co.wm21.https.presenter.interfaces.aplication.OnBDPStatusView
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.adapters.application.BDPStatusAdapter
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar

class BDPStatusFragment : Fragment(), OnBDPStatusView {
    var binding: FragmentBDPBinding? = null
    var presenter: BDPStatusPresenter? = null
    var appSessionManager: SessionHandler? = null
    var checkInternetConnection: CheckInternetConnection? = null
    var user: User? = null
    var loadingDialog: LoadingDialog? = null
    var listData: MutableList<BDPStatusData> = ArrayList()
    var adapter: BDPStatusAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBDPBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        appSessionManager = SessionHandler(activity)
        checkInternetConnection = CheckInternetConnection()
        loadingDialog = LoadingDialog(activity)
        user = User(context)
        presenter = BDPStatusPresenter(this)
        loadIncomeBalanceData()
        return binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadIncomeBalanceData() {
        adapter = BDPStatusAdapter(listData, requireContext()) { item: BDPStatusData? -> }


        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding!!.recyclerView.layoutManager = layoutManager
        binding!!.recyclerView.itemAnimator = DefaultItemAnimator()
        binding!!.recyclerView.adapter = adapter


        if (checkInternetConnection!!.isInternetAvailable(activity)) {
            presenter!!.getDataLoad(user!!.username)
        } else {
            val snackbar = Snackbar.make(
                requireActivity().window.decorView.rootView,
                "No internet connection!",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("RETRY") { loadIncomeBalanceData() }
            snackbar.setActionTextColor(Color.RED)
            val sbView = snackbar.view
            val textView = sbView.findViewById<View>(R.id.snackbar_text) as TextView
            textView.setTextColor(Color.YELLOW)
            snackbar.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBDPStatusDataLoad(response: BDPStatusResponse) {
        listData.clear()
        response.data?.let { listData.addAll(it) }
        adapter!!.notifyDataSetChanged()
    }

    override fun onStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onError(errmsg: String) {
        loadingDialog!!.dismissDialog()
    }
}