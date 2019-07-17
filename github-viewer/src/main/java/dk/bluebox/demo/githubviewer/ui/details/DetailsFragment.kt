package dk.bluebox.demo.githubviewer.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import dk.bluebox.demo.githubviewer.R
import dk.bluebox.demo.githubviewer.databinding.FragmentDetailsBinding
import dk.bluebox.demo.githubviewer.ui.utils.fragmentLayoutBindingInflater
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val layoutBindingInflater = fragmentLayoutBindingInflater<FragmentDetailsBinding>(R.layout.fragment_details)

    private val binding by layoutBindingInflater
    private val viewModel by viewModel<DetailsViewModelImpl>()

    private val arguments by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBindingInflater.inflate(inflater, container)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.list.adapter = PullRequestsAdapter()
        binding.list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = viewModel.title
        viewModel.load(arguments.repositoryId)
    }
}
