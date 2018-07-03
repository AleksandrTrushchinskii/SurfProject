package ru.aleksandrtrushchinskii.surfproject.common.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import javax.inject.Singleton


@Module
class SingletonsModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesAuthentication(firebaseAuth: FirebaseAuth) = Authentication(firebaseAuth)

}