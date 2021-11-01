import React, { Component } from 'react';
import './TopButtons.css';
import EditPlanModal from './EditPlanModal';
import { Stack, Button } from "react-bootstrap";
import { toast } from 'react-toastify';

class TopButtons extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updatePlan = this.updatePlan.bind(this);
    }
    
    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    updatePlan(type) {
        if (type === "add-success") {
            toast.success("El Plan ha sido editado satisfactoriamente!", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 10000
            });
        }
    };

    render() {
        let statusClass = "";
        let name = this.props.name;
        let type = this.props.type;
        let id = this.props.id;
        let authorName = this.props.authorName;
        let description = this.props.description;
        switch (this.props.status) {
            case 'Activo':
                statusClass = 'in-progress';
                break;
            case 'Inactivo':
                statusClass = 'no-progress';
                break;
            case 'Completo':
                statusClass = 'completed';
                break;
            default:
                statusClass = 'unknown';
                break;
        }
        return (
            <Stack className="mt-4" direction="horizontal" gap={3}>
                <Button id="menu-button" onClick={this.openModal} >Editar Plan</Button>{' '}
                <Button id="menu-button"  >Agregar involucrados</Button>{' '}
                <Button variant="warning">Matriz de riesgos</Button>{' '}
                <Button className="ms-auto" variant="light">Generar reporte</Button>{' '}
                <div className="vr" />
                <Button className={statusClass} variant="success">{this.props.status}</Button>{' '}
                <EditPlanModal name={name} type={type} id={id} authorName={authorName}
                description={description} status={this.props.status} show={this.state.show} closeModal={this.closeModal} />
            </Stack>
        );
    }
};
export default TopButtons;
