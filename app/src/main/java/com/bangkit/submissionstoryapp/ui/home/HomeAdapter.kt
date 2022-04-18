package com.bangkit.submissionstoryapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionstoryapp.utils.DiffCallback
import com.bangkit.submissionstoryapp.data.remote.model.ListStoryItem
import com.bangkit.submissionstoryapp.databinding.ItemRowStoriesBinding
import com.bangkit.submissionstoryapp.ui.detail.DetailStoryActivity
import com.bumptech.glide.Glide

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val listStory = ArrayList<ListStoryItem>()

    fun setListStory(itemStory: List<ListStoryItem>) {
        val diffCallback = DiffCallback(this.listStory, itemStory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listStory.clear()
        this.listStory.addAll(itemStory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount() = listStory.size

    inner class ViewHolder(private var binding: ItemRowStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            with(binding) {
                Glide.with(imgItemImage)
                    .load(story.photoUrl) // URL Avatar
//                    .placeholder(R.drawable.ic_place_holder)
//                    .error(R.drawable.ic_broken_image)
                    .into(imgItemImage)
                tvName.text = story.name
//                tvDescription.text = story.description
//                tvCreatedTime.text =
//                    binding.root.resources.getString(R.string.created_add, story.createdAt)

                // image OnClickListener
                imgItemImage.setOnClickListener {
                    val intent = Intent(it.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
                    it.context.startActivity(intent)
//                }
                }
            }
        }

    }
}