package com.example.notebook.notebookdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.notebook.R
import com.example.notebook.database.NotebookDatabase
import com.example.notebook.databinding.FragmentNotebookDetailsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotebookDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotebookDetailsFragment : Fragment() {
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
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment via databinding
        val binding= DataBindingUtil.inflate<FragmentNotebookDetailsBinding>(inflater,R.layout.fragment_notebook_details,container,false)



        //get the args from navigation
        var args=NotebookDetailsFragmentArgs.fromBundle(arguments!!)

        //instantiate database and use databasedao to operate data
        val application = requireNotNull(this.activity).application
        val dataSource= NotebookDatabase.getInstance(application).notebookDatabaseDao

        //instantiate viewmodel via viewmodel factory
        val viewModelFactory = NotebookDetailsViewModelFactory(args.unitNotebookId,dataSource, application)
        val notebookDetailsViewModel= ViewModelProviders.of(this,viewModelFactory).get(
            NotebookDetailsViewModel::class.java)
        binding.notebookDetailsViewModel=notebookDetailsViewModel

        binding.lifecycleOwner=this

        notebookDetailsViewModel.current.observe(this, Observer {
            binding.detailsTime.text=it.timeStamp
            binding.detailsContent.setText(it.content)
        })


        binding.btnSave.setOnClickListener {

            notebookDetailsViewModel.onSaveNotebook(binding.detailsContent.text.toString())
            //hide keyboard
            val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)


        }

        notebookDetailsViewModel.navigatetoNotebookShelf.observe(this, Observer {
            if (it==true){
                this.findNavController().navigate(NotebookDetailsFragmentDirections.actionNotebookDetailsFragmentToNotebookShelfFragment())
                notebookDetailsViewModel.doneNavigating()
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
         * @return A new instance of fragment NotebookDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotebookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}