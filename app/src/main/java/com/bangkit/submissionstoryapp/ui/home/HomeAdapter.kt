package com.bangkit.submissionstoryapp.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem
import com.bangkit.submissionstoryapp.databinding.ItemRowStoriesBinding
import com.bangkit.submissionstoryapp.ui.detail.DetailStoryActivity
import com.bangkit.submissionstoryapp.utils.DiffCallback
import com.bumptech.glide.Glide

class HomeAdapter : PagingDataAdapter<ListStoryItem, HomeAdapter.ViewHolder>(
    DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) as ListStoryItem)

    }

//    override fun getItemCount() = listStory.size

    class ViewHolder(private var binding: ItemRowStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
       private val imgImage: ImageView = binding.imgItemImage
        private val tvName: TextView = binding.tvItemName
        fun bind(story: ListStoryItem) {

            with(binding) {
                Glide.with(imgItemImage)
                    .load(story.photoUrl) // URL Avatar
                    .placeholder(R.drawable.ic_baseline_image_gray)
                    .error(R.drawable.ic_baseline_broken_image_gray)
                    .into(imgItemImage)
                tvItemName.text = story.name
                itemView.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgImage, "profile"),
                            Pair(tvName, "name"),
                        )
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }


            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}