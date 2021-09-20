package com.example.tetrainingandroid.di

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tetrainingandroid.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(ActivityComponent::class)
@Module
object UiModule {
    @Provides
    fun provideDiverDecorationItem(@ActivityContext context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.item_decoration)!!)
        return decoration
    }
}