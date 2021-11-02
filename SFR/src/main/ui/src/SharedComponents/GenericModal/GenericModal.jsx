import React, { Component } from 'react';
import { Modal, Button } from "react-bootstrap";

//<GenericModal show={} close={} action={} header={} body={} />

class GenericModal extends Component {
    render() {
        let showModal = this.props.show;
        let closeModal = this.props.close;
        let doAction = this.props.action;
        let header = this.props.header;
        let body = this.props.body;
        return (
            <Modal show={showModal} onHide={closeModal} >
                <Modal.Header closeButton>
                    {header}
                </Modal.Header>
                <Modal.Body>
                    <p>{body}</p>
                    <Button className='btn-sfr' type="submit" onClick={doAction}>
                        Aceptar
                    </Button>
                    <Button className='btn-sfr' type="button"onClick={closeModal}>
                        Cancelar
                    </Button>
                </Modal.Body>
            </Modal>
        );
    }
};
export default GenericModal;
