package dk.bluebox.demo.githubviewer.features.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.common.ui.core.BaseFragment
import dk.bluebox.demo.githubviewer.databinding.FragmentListBinding
import dk.bluebox.demo.githubviewer.common.ui.core.databinding.fragmentLayoutBindingInflater
import javax.inject.Inject

class ListFragment : BaseFragment() {
    private val layoutBindingInflater =
        fragmentLayoutBindingInflater<FragmentListBinding>(
            R.layout.fragment_list
        )

    private val binding by layoutBindingInflater

    @Inject
    lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBindingInflater.inflate(inflater, container)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.list.adapter =
            RepositoriesAdapter()
        binding.list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = viewModel.title
        viewModel.load()
    }
}
