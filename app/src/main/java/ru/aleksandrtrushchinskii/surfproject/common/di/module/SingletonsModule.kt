package ru.aleksandrtrushchinskii.surfproject.common.di.module

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.service.Internet
import ru.aleksandrtrushchinskii.surfproject.common.service.Toaster
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
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

    @Provides
    @Singleton
    fun provideTodoDatabase(
            firestore: FirebaseFirestore,
            auth: Authentication
    ) = TodoDatabase(firestore, auth)

    @Provides
    @Singleton
    fun providesTodoRepository(todoDatabase: TodoDatabase) = TodoRepository(todoDatabase)

    @Provides
    @Singleton
    fun providesToaster(context: Context) = Toaster(context)

    @Provides
    @Singleton
    fun providesInternet(context: Context, toaster: Toaster) = Internet(context, toaster)

    @Provides
    @Singleton
    fun providesViewModelFactory(
            auth: Authentication,
            todoRepository: TodoRepository
    ) = ViewModelFactory(auth, todoRepository)

}