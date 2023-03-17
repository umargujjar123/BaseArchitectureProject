package  com.example.basearchitectureproject.user_details.usecase

import android.util.Log
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import g5.consultency.cuitalibilam.base.util.Resource
import g5.consultency.cuitalibilam.user_details.models.UploadUserDetailsModel
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class UploadUserDetailsUseCase constructor(
    private val uploadUserDetailsRepository: UploadUserDetailsRepository
) : FlowUseCase<UploadUserDetailsModel, Resource<UploadUserDetailsModel?>>(Dispatchers.IO) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameters: UploadUserDetailsModel) = callbackFlow {
        uploadUserDetailsRepository.uploadUserDetails(parameters)
            .collect { uploadUserDetailsResponse ->
                send(uploadUserDetailsResponse)
            }
        awaitClose { }
    }
}