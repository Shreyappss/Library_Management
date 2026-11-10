package folio.library

import java.time.Instant

enum class RequestStatus { SUBMITTED, UNDER_REVIEW, APPROVED, DECLINED }

data class BookRequest(
    val id: String,
    val studentId: String,
    val title: String,
    val author: String,
    val reason: String,
    val submittedAt: Instant = Instant.now(),
    val status: RequestStatus = RequestStatus.SUBMITTED
) {
    init {
        require(title.isNotBlank()) { "A book title is required" }
        require(author.isNotBlank()) { "An author is required" }
        require(studentId.isNotBlank()) { "A student identifier is required" }
    }
}

/** In-memory request workflow illustrating the Kotlin service/domain layer. */
class BookRequestService {
    private val requests = linkedMapOf<String, BookRequest>()

    @Synchronized
    fun submit(request: BookRequest): BookRequest {
        require(request.id !in requests) { "Request identifier already exists" }
        requests[request.id] = request
        return request
    }

    @Synchronized
    fun updateStatus(id: String, status: RequestStatus): BookRequest {
        val current = requests[id] ?: error("Request not found")
        return current.copy(status = status).also { requests[id] = it }
    }

    @Synchronized
    fun allRequests(): List<BookRequest> = requests.values.toList()
}
