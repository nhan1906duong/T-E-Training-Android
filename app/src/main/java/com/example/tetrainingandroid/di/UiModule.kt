package com.example.tetrainingandroid.di

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.people.adapter.CastDiffCallback
import com.example.tetrainingandroid.ui.people.adapter.CrewDiffCallback
import com.example.tetrainingandroid.ui.people.adapter.PeopleAdapter
import com.example.tetrainingandroid.ui.people.adapter.PeopleViewHolderType
import com.example.tetrainingandroid.ui.people.adapter.career.CareerAdapter
import com.example.tetrainingandroid.ui.people.adapter.career.CareerDiffCallback
import com.example.tetrainingandroid.ui.people.adapter.career.CareerViewHolderType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Qualifier

@Qualifier
annotation class DividerHorizontal8

@Qualifier
annotation class DividerHorizontal16

@Qualifier
annotation class DividerVertical16

@Qualifier
annotation class CrewAdapter

@Qualifier
annotation class CastAdapter

@Qualifier
annotation class CareerAsCrewAdapter

@Qualifier
annotation class CareerAsCastAdapter

@InstallIn(ActivityComponent::class)
@Module
object UiModule {

    @DividerVertical16
    @Provides
    fun provideDiverDecorationVertical16Item(@ActivityContext context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.item_vertical_decoration_16
            )!!
        )
        return decoration
    }

    @DividerHorizontal16
    @Provides
    fun provideDiverDecorationHorizontal16Item(@ActivityContext context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.item_horizontal_decoration_16
            )!!
        )
        return decoration
    }

    @DividerHorizontal8
    @Provides
    fun provideDiverDecorationHorizontal8Item(@ActivityContext context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.item_horizontal_decoration_8
            )!!
        )
        return decoration
    }

    @CrewAdapter
    @Provides
    fun provideCrewAdapter(
        diffCallback: CrewDiffCallback,
        @DividerVertical16 decoration: DividerItemDecoration
    ): PeopleAdapter {
        return PeopleAdapter(
            type = PeopleViewHolderType.CREW,
            diffCallback = diffCallback,
            dividerItemDecoration = decoration
        )
    }

    @CastAdapter
    @Provides
    fun provideCastAdapter(
        diffCallback: CastDiffCallback,
        @DividerHorizontal16 decoration: DividerItemDecoration
    ): PeopleAdapter {
        return PeopleAdapter(
            type = PeopleViewHolderType.CAST,
            diffCallback = diffCallback,
            dividerItemDecoration = decoration
        )
    }

    @CareerAsCastAdapter
    @Provides
    fun provideCareerAsCastAdapter(
        diffCallback: CareerDiffCallback,
    ): CareerAdapter {
        return CareerAdapter(
            CareerViewHolderType.Cast,
            diffCallback = diffCallback,
        )
    }

    @CareerAsCrewAdapter
    @Provides
    fun provideCareerAsCrewAdapter(
        diffCallback: CareerDiffCallback,
    ): CareerAdapter {
        return CareerAdapter(
            CareerViewHolderType.Crew,
            diffCallback = diffCallback,
        )
    }
}