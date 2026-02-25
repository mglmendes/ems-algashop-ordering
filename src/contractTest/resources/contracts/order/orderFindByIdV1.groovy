package contract.order

import com.algaworks.algashop.ordering.presentation.model.PageModel
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept "application/json"
        }
        url("/api/v1/orders")
    }

    response {
        status 200
    }
}

