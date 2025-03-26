package com.yaduvanshi_ashok_rd.instagramclone.Fragmetns

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.yaduvanshi_ashok_rd.instagramclone.R
import com.yaduvanshi_ashok_rd.instagramclone.dialogActivity


class AddFragment : Fragment() {
private lateinit var post: Button
private lateinit var Reel: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post = view.findViewById(R.id.post)
        Reel = view.findViewById(R.id.Reel)

        post.setOnClickListener {
            startActivity(
                Intent(requireContext(), dialogActivity::class.java)
            )
        }

    }

}