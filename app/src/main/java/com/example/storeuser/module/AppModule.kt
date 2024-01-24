package com.example.storeuser.module

import android.content.Context
import com.example.storeuser.data.OnBoardingPreferences
import com.example.storeuser.data.StoreRepository
import com.example.storeuser.screen.Favorite.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideRepository() = StoreRepository()

    @Singleton
    @Provides
    fun provideDataStoreOperation(
        @ApplicationContext context:Context
    ) =OnBoardingPreferences(context)
}