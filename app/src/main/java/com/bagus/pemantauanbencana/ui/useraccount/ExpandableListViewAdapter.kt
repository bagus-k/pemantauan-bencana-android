package com.bagus.pemantauanbencana.ui.useraccount

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bagus.pemantauanbencana.R
import org.w3c.dom.Text
import java.text.FieldPosition

class ExpandableListViewAdapter internal constructor(private val  context: Context ,private val listGroup: ArrayList<String>, private val listChild: HashMap<String, ArrayList<String>>): BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listGroup.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listChild.get(listGroup.get(groupPosition))?.size!!
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.listGroup.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.listChild[this.listGroup[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var groupTitle = getGroup(groupPosition).toString()

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.expandable_chapter_list, null)
        }

        val groupImg = convertView!!.findViewById<ImageView>(R.id.img_chapter)
        val groupTv = convertView!!.findViewById<TextView>(R.id.tv_chapter)

        if (groupTitle == "Definisi Aplikasi") {
            groupImg.setImageResource(R.drawable.ic_smartphone_black)
        } else if (groupTitle == "Cara Kerja Aplikasi") {
            groupImg.setImageResource(R.drawable.ic_donut_black)
        } else if (groupTitle == "Sumber Data") {
            groupImg.setImageResource(R.drawable.ic_api_black)
        } else if (groupTitle == "Versi Aplikasi") {
            groupImg.setImageResource(R.drawable.ic_app_settings_black)
        } else {
            groupImg.setImageResource(R.drawable.ic_smartphone_black)
        }

        groupTv.text = groupTitle

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var groupTitle = getChild(groupPosition, childPosition) as String

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.expandable_topic_list, null)
        }

        val childTv = convertView!!.findViewById<TextView>(R.id.tv_topic_list)
        childTv.text = groupTitle

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}