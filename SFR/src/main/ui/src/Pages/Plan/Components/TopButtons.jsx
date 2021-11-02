import React, { Component } from 'react';
import './TopButtons.css';
import EditPlanModal from './EditPlanModal';
import { Stack, Button } from "react-bootstrap";
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';
//import { toast } from 'react-toastify';

class TopButtons extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            showDel: false
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
    }

    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    openModalDelete = () => {
        this.setState({ showDel: true });
    };

    closeModalDelete = () => {
        this.setState({ showDel: false });
    };

    render() {
        let statusClass = "";
        let name = this.props.name;
        let type = this.props.type;
        let id = this.props.id;
        let authorName = this.props.authorName;
        let description = this.props.description;
        //let entryDate = this.props.entryDate;
        //let riskList = this.props.riskList;
        let refreshPage = this.props.refreshPage;
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
                <Button id="menu-button" onClick={this.openModalDelete} >Eliminar Plan</Button>{' '}
                <Button id="menu-button"  >Agregar involucrados</Button>{' '}
                <Button variant="warning">Matriz de riesgos</Button>{' '}
                <Button className="ms-auto" variant="light">Generar reporte</Button>{' '}
                <div className="vr" />
                <Button className={statusClass} variant="success">{this.props.status}</Button>{' '}
                <EditPlanModal
                    name={name}
                    type={type}
                    id={id}
                    authorName={authorName}
                    description={description}
                    status={this.props.status}
                    entryDate={this.props.entryDate}
                    riskList={this.props.riskList}
                    show={this.state.show}
                    closeModal={this.closeModal}
                    refreshPage={refreshPage} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelete}
                    action={this.props.deletePlan}
                    header={"Eliminar Plan"}
                    body={"¿Desea eliminar este plan? Una vez eliminado no se podra recuperar el plan seleccionado"} />
            </Stack>
        );
    }
};
export default TopButtons;
