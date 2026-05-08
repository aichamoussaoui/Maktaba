package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://fdcyqgieezwflpqataot.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZkY3lxZ2llZXp3ZmxwcWF0YW90Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzgxNjE4MjQsImV4cCI6MjA5MzczNzgyNH0.j_gUqAj7RoCkhlejdkiD5etdK6LOyPTeooux_rJS24Q"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}