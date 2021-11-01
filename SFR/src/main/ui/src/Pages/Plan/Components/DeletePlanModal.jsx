import React, { Component } from 'react';
import '../Plan.css'
import { Modal, Button } from "react-bootstrap";
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class DeletePlanModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    //closeModal() { }

    handleSubmit = (event) => {
        event.preventDefault();
        let options = {
            url: `http://localhost:8080/SFR/API/PlanManager/delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'name': event.target.name.value,
                'id': event.target.id.value,
                'status': event.target.status.value,
                'authorName': event.target.authorName.value,
                'type': event.target.type.value,
                'description': event.target.description.value
            }
        }
        axios(options)
            .then(response => {
                //this.props.updatePlanes("add-success");
                this.props.closeModalDelete();
            })
    }

    render() {
        let render = this.props.showDel
        let closeModal = this.props.closeModalDelete
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header closeButton>
                    Eliminar Plan
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <h1> ¿Desea eliminar este plan? </h1>
                        <h4> Una vez eliminado no se podra recuperar el plan seleccionado </h4>
                    </div>
                    <Button className='btn-sfr' type="submit" id="submit-button-delete-item">
                        Aceptar
                    </Button>
                    <Button className='btn-sfr' type="button" id="cancel-button" onClick={closeModal}>
                        Cancelar
                    </Button>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default DeletePlanModal;
