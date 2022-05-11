package com.example.tasks.view.holder

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.tasks.R
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.repository.TasksRepository
import com.example.tasks.service.repository.remote.PriorityRepository

@EpoxyModelClass
abstract class TaskHolder() : EpoxyModelWithHolder<TaskHolder.mTaskHolder>() {

    private val mPriorityRepository = PriorityRepository()
    private val mTaskRepository = TasksRepository()

    @EpoxyAttribute
    var image: Boolean = false

    @EpoxyAttribute
    var idKey:Int = 0

    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    var priority: Int = 0

    @EpoxyAttribute
    lateinit var dueDate: String

    override fun bind(holder: mTaskHolder) {
        setImage(holder)
        setPriority(holder)
        holder.mDescription.text = description
        holder.mDueDate.text = dueDate

        holder.mContainerCard.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(it.context)
            alertBuilder.setTitle("Deletar a task?")
            alertBuilder.setMessage("Essa task será deletada permanentemente.")
            alertBuilder.setPositiveButton("Sim") { _, _ ->
                deleteTask(idKey, it.context)
            }

            alertBuilder.create().show()
        }
    }

    private fun setImage(holder: mTaskHolder) {
        if (image) {
            holder.mImage.setImageResource(R.drawable.ic_done)
        }
    }

    private fun setPriority(holder: mTaskHolder) {
        mPriorityRepository.getPriority(object : APIListener<List<PriorityModel>> {
            override fun onSuccess(model: List<PriorityModel>) {
                for (priorityLine in model) {
                    if (priorityLine.id == priority) {
                        holder.mPriority.text = priorityLine.description
                    }
                }
            }

            override fun onFailure(message: String) {
                throw Error(message)
            }

        })
    }

    private fun deleteTask(id: Int, context: Context) {
        mTaskRepository.deleteTask(id, object : APIListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                Toast.makeText(context, "Task deletada com sucesso", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(message: String) {
                Toast.makeText(context, "Error ao deletar task, $message", Toast.LENGTH_LONG).show()
            }

        })
    }

    inner class mTaskHolder : EpoxyHolder() {
        lateinit var mImage: ImageView
        lateinit var mDescription: TextView
        lateinit var mPriority: TextView
        lateinit var mDueDate: TextView
        lateinit var mContainerCard: ConstraintLayout

        override fun bindView(itemView: View) {
            mImage = itemView.findViewById(R.id.image_task)
            mDescription = itemView.findViewById(R.id.text_description)
            mPriority = itemView.findViewById(R.id.text_priority)
            mDueDate = itemView.findViewById(R.id.text_due_date)
            mContainerCard = itemView.findViewById(R.id.container_card)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.row_task_list
    }
}