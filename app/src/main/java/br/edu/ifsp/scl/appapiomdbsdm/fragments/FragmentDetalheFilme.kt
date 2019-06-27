package br.edu.ifsp.scl.omdbapisdmkt.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.appomdbapisdm.R
import br.edu.ifsp.scl.appomdbapisdm.data.OMDb
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.codigosMensagen.RESPOSTA_BUSCA
import br.edu.ifsp.scl.omdbapisdmkt.utils.OmdbItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhe_filme.*


class FragmentDetalheFilme : ModoApp() {

    private var item: OMDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = if(view != null) view else
    inflater.inflate(R.layout.fragment_detalhe_filme, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState != null) {
            item = savedInstanceState.getParcelable("item")
            bind()
        } else {
            itemHandler = BuscaHandler()
            OmdbItem(this).buscar(arguments!!.getString("imdbID"))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("item", item)
    }

    companion object { fun newInstance(): FragmentDetalheFilme = FragmentDetalheFilme() }

    lateinit var itemHandler: BuscaHandler // Handler da thread de UI

    inner class BuscaHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == RESPOSTA_BUSCA) {
                item = msg.obj as OMDb
                bind()
            }
        }
    }

    fun bind(){
        if(item?.Type == "movie") {
            dvdValueTextView.text = item?.DVD
            boxOfficeValueTextView.text = item?.BoxOffice
            productionValueTextView.text = item?.Production
            websiteValueTextView.text = item?.Website
        } else {
            sourceDoisTextView.visibility = View.GONE
            sourceDoisValueTextView.visibility = View.GONE
            sourceTresTextView.visibility = View.GONE
            sourceTresValueTextView.visibility = View.GONE
            dvdTextView.visibility = View.GONE
            dvdValueTextView.visibility = View.GONE
            boxOfficeTextView.visibility = View.GONE
            boxOfficeValueTextView.visibility = View.GONE
            productionTextView.visibility = View.GONE
            productionValueTextView.visibility = View.GONE
            websiteTextView.visibility = View.GONE
            websiteValueTextView.visibility = View.GONE
            writerTextView.visibility = View.GONE
            writerValueTextView.visibility = View.GONE
        }
        titleTextView.text = item?.Title
        yearValueTextView.text = item?.Year
        ratedValueTextView.text = item?.Rated
        releasedValueTextView.text = item?.Released
        runtimeValueTextView.text = item?.Runtime
        genreValueTextView.text = item?.Genre
        directorValueTextView.text = item?.Director
        writerValueTextView.text = item?.Writer
        actorsValueTextView.text = item?.Actors
        plotValueTextView.text = item?.Plot
        languageValueTextView.text = item?.Language
        countryValueTextView.text = item?.Country
        awardsValueTextView.text = item?.Awards
        metascoreValueTextView.text = item?.Metascore
        imdbRatingValueTextView.text = item?.imdbRating
        imdbVotesValueTextView.text = item?.imdbVotes
        typeValueTextView.text = item?.Type

        Picasso.get()
            .load(item?.Poster)
            .placeholder(R.drawable.app_icon) // while image is charging
            .error(R.drawable.app_icon) // case error
            .into(posterImageView)

        val tvSource = listOf(sourceUmTextView, sourceDoisTextView, sourceTresTextView)
        val tvSourceValue = listOf(sourceUmValueTextView, sourceDoisValueTextView, sourceTresValueTextView)

        for (i in 0 until (item?.Ratings?.size!!)-1) {
            tvSource[i]?.text = item?.Ratings?.get(i)?.Source
            tvSourceValue[i]?.text = item?.Ratings?.get(i)?.Value
        }
    }
}