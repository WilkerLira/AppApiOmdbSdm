package br.edu.ifsp.scl.omdbapisdmkt.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import br.edu.ifsp.scl.appomdbapisdm.R
import br.edu.ifsp.scl.appomdbapisdm.adapter.ListAdapter
import br.edu.ifsp.scl.appomdbapisdm.data.Search
import br.edu.ifsp.scl.omdbapisdmkt.fragment.ModoApp.codigosMensagen.RESPOSTA_BUSCA
import br.edu.ifsp.scl.omdbapisdmkt.utils.OmdbSearch
import kotlinx.android.synthetic.main.fragment_omdb.*
import org.jetbrains.anko.design.snackbar

class FragmentListaFilmes : ModoApp() {

    private var itens = ArrayList<Search>()
    private var sv: SearchView? = null
    private var svQuery: SearchView.OnQueryTextListener? = null
    private var omdbSearch = OmdbSearch(this)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if(searchItem != null) sv = searchItem.actionView as SearchView
        if(sv != null) sv?.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        svQuery = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("onQueryTextChange", newText)
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("onQueryTextSubmit", query)
                var imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(sv?.windowToken, 0)
                omdbSearch.buscar(query)
                return true
            }
        }
        sv?.setOnQueryTextListener(svQuery)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_omdb, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(itens.size > 0) {
            bind()
        } else {
            buscaHandler = BuscaHandler()
            omdbSearch.buscar("galaxy")
        }
    }

    companion object { fun newInstance(): FragmentListaFilmes = FragmentListaFilmes() }

    lateinit var buscaHandler: BuscaHandler // Handler da thread de UI

    inner class BuscaHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg?.what == RESPOSTA_BUSCA) {
                itens = msg.obj as ArrayList<Search>
                bind()
            }
        }
    }

    private fun bind() {
        if(itens.size > 0) {
            list_recycler_view.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
            list_recycler_view.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = ListAdapter(itens) {
                    list_recycler_view.snackbar("clicou no item ${itens[it].Title}")
                    val fragment = FragmentDetalheFilme()
                    val bundle = Bundle()
                    bundle.putString("imdbID", itens[it].imdbID)
                    fragment.arguments = bundle
                    fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, fragment)?.addToBackStack(null)
                        ?.commit()
                }
            }
        } else {
            list_recycler_view.visibility = View.GONE
            empty_view.visibility = View.VISIBLE
        }
    }
}