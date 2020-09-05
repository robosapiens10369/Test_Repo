package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="[TEST] Color Test", group="K9bot")

public class Colorsensor_test extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumHardware robot = new MecanumHardware();


    @Override
    public void runOpMode() {



        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        robot.FrontColorSensor = hardwareMap.colorSensor.get("Color0");
        robot.FrontDistanceSensor = hardwareMap.opticalDistanceSensor.get("Color0");

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            //Report the sensor colors to the user
            telemetry.addData("distance-sensor",robot.FrontDistanceSensor.getLightDetected());
            telemetry.addData("C.S. RED", robot.FrontColorSensor.red()); // Red channel value
            telemetry.addData("C.S. GREEN", robot.FrontColorSensor.green());// Green channel value
            telemetry.addData("C.S. BLUE", robot.FrontColorSensor.blue());// Blue channel value

            telemetry.addData("color-sensor alpha", robot.FrontColorSensor.alpha());// Total luminosity
            telemetry.addData("color-sensor argb", robot.FrontColorSensor.argb()); // Combined color value
            telemetry.addData("distance-sensor Raw light",    robot.FrontDistanceSensor.getRawLightDetected());
            telemetry.addData("distance-sensor Normal light", robot.FrontDistanceSensor.getLightDetected());
            telemetry.update();
            idle();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }

    }

}
