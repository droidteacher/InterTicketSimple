package hu.zsoltkiss.interticketsimple.ui.countries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.zsoltkiss.interticketsimple.R
import hu.zsoltkiss.interticketsimple.data.model.Country
import hu.zsoltkiss.interticketsimple.hide
import hu.zsoltkiss.interticketsimple.show
import hu.zsoltkiss.interticketsimple.showWithText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class CountryListActivity : AppCompatActivity() {

    private val viewModel: CountriesViewModel by inject()

    private lateinit var recyclerView: RecyclerView
    private lateinit var infoTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshButton: Button

    private val countryListAdapter = CountryListAdapter()

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        infoTextView = findViewById(R.id.infoTextView)

        recyclerView = findViewById(R.id.countriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = countryListAdapter

        refreshButton = findViewById(R.id.btnRefresh)
        refreshButton.setOnClickListener {
            fetch()
        }

    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    private fun fetch() {
        progressBar.show()
        infoTextView.hide()
        viewModel.countries
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                this::fetchSuccess,
                this::fetchFailed
        ).addTo(disposables)
    }

    private fun fetchSuccess(items: List<Country>) {
        progressBar.hide()
        when(items.isEmpty()) {
            true -> infoTextView.showWithText(getString(R.string.empty_list))
            else -> {
                countryListAdapter.updateItems(items)
                infoTextView.hide()
            }
        }
    }

    private fun fetchFailed(error: Throwable) {
        error.printStackTrace()
        progressBar.hide()
        infoTextView.showWithText(getString(R.string.fetching_error))
    }

}