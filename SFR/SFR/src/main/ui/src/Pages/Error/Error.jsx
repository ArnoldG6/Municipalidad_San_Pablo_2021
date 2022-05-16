import React, { Component } from 'react';
import { Row, Col } from "react-bootstrap";

class Error extends Component {
    render() {
        let status = this.props.status
        let text = this.props.text
        const allowed = [401, 403, 404, 500]
        if (!allowed.includes(status)) {
            status = 500
            text = 'INTERNAL SERVER ERROR'
        }
        return (
            <div className="container">
                <Row>
                    <Col>
                        <h1 className="d-flex justify-content-center">{status}</h1>
                        <h2 className="d-flex justify-content-center">{text}</h2>
                    </Col>
                </Row>
            </div>
        )
    }
}
export default Error;