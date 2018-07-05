package ru.aleksandrtrushchinskii.surfproject.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sign_in_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Internet
import ru.aleksandrtrushchinskii.surfproject.common.service.Toaster
import ru.aleksandrtrushchinskii.surfproject.common.tools.RC_SIGN_IN
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflate
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import javax.inject.Inject


class SignInFragment : DaggerFragment() {

    @Inject
    lateinit var internet: Internet

    @Inject
    lateinit var toaster: Toaster


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container.inflate(R.layout.sign_in_fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingState.stop()

        signInButton.setOnClickListener {
            internet.ifAvailable {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                                .build(),
                        RC_SIGN_IN
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Navigation.finishCurrentFragment()
            } else {
                toaster.signInWasFailed()
            }
        }
    }

}