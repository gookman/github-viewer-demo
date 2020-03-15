package dk.bluebox.demo.githubviewer.common.di

import dagger.Module
import dk.bluebox.demo.githubviewer.features.list.di.ListModule
import dk.bluebox.demo.githubviewer.features.details.di.DetailsModule

@Module(includes = [ListModule::class, DetailsModule::class])
class FeaturesModule
