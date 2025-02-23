import { LocationController } from "../controllers/LocationController";
import { body } from "express-validator";

const controller = new LocationController();

export const LocationRoutes = [
    {
        method: "get",
        route: "/location",
        action: controller.getLocations,
    },
    {
        method: "post",
        route: "/location",
        action: controller.postUserLocation,
        validation: [
            body("userID").notEmpty().isString(),
            body("location").notEmpty()
        ]
    },
    {
        method: "get",
        route: "/location/:userID",
        action: controller.getUserLocation,
        validation: [
            body("userID").notEmpty().isString()
        ]
    },
    {
        method: "put",
        route: "/location/:userID",
        action: controller.updateUserLocation,
        validation: [
            body("userID").notEmpty().isString(),
            body("location").notEmpty()
        ]
    },
    {
        method: "delete",
        route: "/location/:userID",
        action: controller.deleteUserLocation,
        validation: [
            body("userID").notEmpty().isString()
        ]
    },
]