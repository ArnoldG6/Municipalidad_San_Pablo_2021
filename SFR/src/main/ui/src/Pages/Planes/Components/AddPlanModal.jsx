import React, { Component } from 'react';
import axios from 'axios';
import '../Planes.css'
import { Modal, Button, Form, OverlayTrigger, Tooltip, Stack } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class AddPlanModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: "Evaluar, Dirigir y Monitorear"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.createID = this.createID.bind(this);
    }

    onChange = e => {
        console.log(e.target.value)
        this.setState({ value: e.target.value })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/insert`,
            method: 'POST',
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
                this.props.updatePlanes("add-success");
                this.props.closeModal();
            }).catch(error => {
                toast.error("ID del plan ya se encuentra registrado en el sistema.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });

    }

    /**createID(type, subtype) {
        let id = "";
        switch (type) {
            case "Evaluar, Dirigir y Monitorear": {
                break;
            }
            case "Alinear, Planificar y Organizar": {
                break;
            }
            case "Construir, Adquirir e Implementar": {
                break;
            }
            
        }
    }*/

    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header closeButton>
                    Ingrese los datos para el nuevo Plan
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" required />
                        </div>
                        <div className="form-group">
                            <label>Autor:</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" disabled />
                        </div>
                        <div className="form-group">
                            <label>Estado:</label>
                            <Form.Select name="status" id="status">
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Completo">Completo</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Tipo:</label>
                            <Form.Select name="type" id="type" onClick={this.onChange}>
                                <option value="Evaluar, Dirigir y Monitorear">Evaluar, Dirigir y Monitorear</option>
                                <option value="Alinear, Planificar y Organizar">Alinear, Planificar y Organizar</option>
                                <option value="Construir, Adquirir e Implementar">Construir, Adquirir e Implementar</option>
                                <option value="Entregar, Dar servicio y soporte">Entregar, Dar servicio y soporte</option>
                                <option value="Monitorear, Evaluar y Valorar">Monitorear, Evaluar y Valorar</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Subtipo:</label>
                            <Form.Select name="subtype" id="subtype" hidden={!(this.state.value === "Evaluar, Dirigir y Monitorear")}>
                                <option value="Asegurar el establecimiento y el mantenimiento del marco de Gobierno">
                                    Asegurar el establecimiento y el mantenimiento del marco de Gobierno
                                </option>
                                <option value="Asegurar la obtención de beneficios">
                                    Asegurar la obtención de beneficios
                                </option>
                                <option value="Asegurar la optimización del riesgo">
                                    Asegurar la optimización del riesgo
                                </option>
                                <option value="Asegurar la optimización de los recursos">
                                    Asegurar la optimización de los recursos
                                </option>
                                <option value="Asegurar el compromiso de las partes interesadas">
                                    Asegurar el compromiso de las partes interesadas
                                </option>
                            </Form.Select>
                            <Form.Select name="subtype" id="subtype" hidden={!(this.state.value === "Alinear, Planificar y Organizar")}>
                                <option value="Gestionar el marco de gestión de I&amp;T">
                                    Gestionar el marco de gestión de I&amp;T
                                </option>
                                <option value="Gestionar la estrategia">
                                    Gestionar la estrategia
                                </option>
                                <option value="Gestionar la arquitectura empresarial">
                                    Gestionar la arquitectura empresarial
                                </option>
                                <option value="Gestionar la innovación">
                                    Gestionar la innovación
                                </option>
                                <option value="Gestionar el portafolio">
                                    Gestionar el portafolio
                                </option>
                                <option value="Gestionar el presupuesto y los costos">
                                    Gestionar el presupuesto y los costos
                                </option>
                                <option value="Gestionar los recursos humanos">
                                    Gestionar los recursos humanos
                                </option>
                                <option value="Gestionar las relaciones">
                                    Gestionar las relaciones
                                </option>
                                <option value="Gestionar los acuerdos de servicio">
                                    Gestionar los acuerdos de servicio
                                </option>
                                <option value="Gestionar los proveedores">
                                    Gestionar los proveedores
                                </option>
                                <option value="Gestionar la calidad">
                                    Gestionar la calidad
                                </option>
                                <option value="Gestionar el riesgo">
                                    Gestionar el riesgo
                                </option>
                                <option value="Gestionar la seguridad">
                                    Gestionar la seguridad
                                </option>
                                <option value="Gestionar los datos">
                                    Gestionar los datos
                                </option>

                            </Form.Select>
                            <Form.Select name="subtype" id="subtype" hidden={!(this.state.value === "Construir, Adquirir e Implementar")}>
                                <option value="Gestionar los programas">
                                    Gestionar los programas
                                </option>
                                <option value="Gestionar la definición de requisitos">
                                    Gestionar la definición de requisitos
                                </option>
                                <option value="Gestionar la identificación y construcción de soluciones">
                                    Gestionar la identificación y construcción de soluciones
                                </option>
                                <option value="Gestionar la disponibilidad y capacidad">
                                    Gestionar la disponibilidad y capacidad
                                </option>
                                <option value="Gestionar el cambio organizativo">
                                    Gestionar el cambio organizativo
                                </option>
                                <option value="Gestionar los cambios de TI">
                                    Gestionar los cambios de TI
                                </option>
                                <option value="Gestionar la aceptación y la transición de  los cambios de TI">
                                    Gestionar la aceptación y la transición de  los cambios de TI
                                </option>
                                <option value="Gestionar el conocimiento">
                                    Gestionar el conocimiento
                                </option>
                                <option value="Gestionar los activos">
                                    Gestionar los activos
                                </option>
                                <option value="Gestionar la configuración">
                                    Gestionar la configuración
                                </option>
                                <option value="Gestionar los proyectos">
                                    Gestionar los proyectos
                                </option>
                            </Form.Select>
                            <Form.Select name="subtype" id="subtype" hidden={!(this.state.value === "Entregar, Dar servicio y soporte")}>
                                <option value="Gestionar las operaciones">
                                    Gestionar las operaciones
                                </option>
                                <option value="Gestionar las peticiones y los incidentes de servicio">
                                    Gestionar las peticiones y los incidentes de servicio
                                </option>
                                <option value="Gestionar los problemas">
                                    Gestionar los problemas
                                </option>
                                <option value="Gestionar la continuidad">
                                    Gestionar la continuidad
                                </option>
                                <option value="Gestionar los servicios de seguridad">
                                    Gestionar los servicios de seguridad
                                </option>
                                <option value="Gestionar los controles de procesos de negocio">
                                    Gestionar los controles de procesos de negocio
                                </option>
                            </Form.Select>
                            <Form.Select name="subtype" id="subtype" hidden={!(this.state.value === "Monitorear, Evaluar y Valorar")}>
                                <option value="Gestionar el monitoreo del desempeño y la conformidad">
                                    Gestionar el monitoreo del desempeño y la conformidad
                                </option>
                                <option value="Gestionar el sistema de control interno">
                                    Gestionar el sistema de control interno
                                </option>
                                <option value="Gestionar  el cumplimiento delos requerimientos externos">
                                    Gestionar  el cumplimiento delos requerimientos externos
                                </option>
                                <option value="Gestionar  el aseguramiento">
                                    Gestionar  el aseguramiento
                                </option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Descripción:</label>
                                <OverlayTrigger

                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_PLANES_HELP_DESC}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" style={{ height: '150px' }} />
                        </div>
                        <div className='text-center'>
                            <Button className='btn-sfr' type="submit">
                                Guardar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default AddPlanModal;