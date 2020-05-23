package com.example.notebook.notebookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.notebook.R
import com.example.notebook.database.NotebookDatabase
import com.example.notebook.databinding.FragmentNotebookShelfBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotebookShelfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotebookShelfFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment via databinding
        val binding=DataBindingUtil.inflate<FragmentNotebookShelfBinding>(inflater,R.layout.fragment_notebook_shelf,container,false)

        binding.lifecycleOwner = this

        //instantiate database and use databasedao to operate data
        val application = requireNotNull(this.activity).application
        val dataSource=NotebookDatabase.getInstance(application).notebookDatabaseDao

        //instantiate viewmodel via viewmodel factory
        val viewModelFactory = NotebookShelfViewModelFactory(dataSource, application)
        val notebookShelfViewModel= ViewModelProviders.of(this,viewModelFactory).get(NotebookShelfViewModel::class.java)

        //binding the viewmodel to layout
        binding.notebookShelfViewModel=notebookShelfViewModel


        //instantiate recycler adapter
        var notebookShelfAdapter=NotebookShelfAdapter()
        binding.notebookList.adapter=notebookShelfAdapter


        //obersve notebooklist in viewmodel and assign it to adapter
        //show the notebook list
        notebookShelfViewModel.notebookList.observe(viewLifecycleOwner, Observer {
            notebookShelfAdapter.data=it
        })

        notebookShelfViewModel.navigateToNotebookDetails.observe(viewLifecycleOwner, Observer {
            it?.let{
                this.findNavController().navigate(
                    NotebookShelfFragmentDirections.actionNotebookShelfFragmentToNotebookDetailsFragment(it.notebookId))
                notebookShelfViewModel.doneNavigating()

            }
        })












        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotebookShelfFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotebookShelfFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}