package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Player extends GameObject {
    public static int score;
    private final Vector2 velocity;
    private final int speed;

    public Player(Rectangle bounds, World world) {
        super(bounds, world, "player-ship.png", "laser.wav");
        score = 0;
        velocity = new Vector2();
        speed = 10;
    }

    @Override
    protected Body createBody() {
        return Box2DHelper.createBody(
            new Box2DBody(actualBounds, 5, actualWorld, this)
        );
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velocity.x = 1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            velocity.x = -1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.W))
            velocity.y = 1.5f;

        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            velocity.y = -1.5f;

        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);

        velocity.scl(0);

        manageScreenWrapping();
    }

    private void manageScreenWrapping() {

        if (getPixelPosition().x > 960)
            body.setTransform(1 / PIXELS_PER_METER, body.getPosition().y, 0);

        else if (getPixelPosition().x < 0)
            body.setTransform(960 / PIXELS_PER_METER, body.getPosition().y, 0);

        else if (getPixelPosition().y > 544)
            body.setTransform(body.getPosition().x, 1 / PIXELS_PER_METER, 0);

        else if (getPixelPosition().y < 0)
            body.setTransform(body.getPosition().x, 544 / PIXELS_PER_METER, 0);
    }

    public Vector2 getPixelPosition() {
        return new Vector2(
            body.getPosition().x * PIXELS_PER_METER,
            body.getPosition().y * PIXELS_PER_METER
        );
    }
}
