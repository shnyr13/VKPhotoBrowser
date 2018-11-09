package padev.vkphotobrowser.application.mvp.view

interface ISignInView {

    fun startLoading()

    fun endLoading()

    fun showError(errorText: Int)

    fun showFriends()
}