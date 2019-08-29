package com.sheldon.bujofe.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.NoticeItem
import com.sheldon.bujofe.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.homeRecyclerViewNotice.adapter = NoticeItemAdapter()


//        Mock data for RecyclerView
        val textlist: ArrayList<NoticeItem> = ArrayList()
        textlist.add(NoticeItem("這世界真的太美麗，有時間真的可以好好補個習"))
        textlist.add(NoticeItem("獎學金公告~~~以下的同學有福拉:\n" +
                "        XXX\n" +
                "        OOO\n" +
                "        CCC\n" +
                "        EEE\n"))
        textlist.add(NoticeItem("公告~~~有好多同學希望開班:\n" +
                "        我們都聽到需求了\n" +
                "        化學 3 班\n" +
                "        物理 12 班\n" +
                "        國文 2 班\n"))
        textlist.add(NoticeItem("颱風警告 這禮拜總共有10個颱風\n" +
                "        補習班將停課一周\n"))

        (binding.homeRecyclerViewNotice.adapter as NoticeItemAdapter).submitList(textlist)



        return binding.root
    }
}
